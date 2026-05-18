<template>
  <div class="rich-text-editor">
    <div class="editor-container" :style="{ height: editorHeight }">
      <WangToolbar
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
        mode="default" 
        class="editor-toolbar"
      />

      <WangEditor
        v-model="content"
        :defaultConfig="editorConfig"
        mode="default"
        class="wang-editor"
        @onCreated="handleCreated"
        @onChange="handleChange"
        @onDestroyed="handleDestroyed"
      />
    </div>

    <div v-if="showWordCount" class="editor-footer">
      <div class="word-count">
        <span class="count-text">{{ currentCharCount }} / {{ maxCharCount }}</span>
        <div class="process-bar">
          <div :style="{ width: Math.min((currentCharCount / maxCharCount) * 100, 100) + '%' }"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import '@wangeditor/editor/dist/css/style.css'
import { ElMessage } from 'element-plus'
import { onBeforeUnmount, ref, shallowRef, watch, computed } from 'vue'
import { Editor as WangEditor, Toolbar as WangToolbar } from '@wangeditor/editor-for-vue'
import { uploadFile } from '@/api/admin'
import { filebaseURL } from '@/config/index'

// Props 定义
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  placeholder: {
    type: String,
    default: '请输入内容'
  },
  height: {
    type: String,
    default: '500px'
  },
  showWordCount: {
    type: Boolean,
    default: true
  },
  maxCharCount: {
    type: Number,
    default: 5000
  },
  disabled: {
    type: Boolean,
    default: false
  }
})

// 事件定义
const emit = defineEmits(['update:modelValue', 'change'])

// 编辑器实例引用
const editorRef = shallowRef(null)

// 编辑器内容
const content = ref(props.modelValue)

// 编辑器高度
const editorHeight = computed(() => props.height)

// 当前字符数
const currentCharCount = computed(() => {
  if (!content.value) return 0
  const text = editorRef.value?.getText()?.trim() || ''
  return text.length
})

// 工具栏配置
const toolbarConfig = {
  toolbarKeys: [
    'bold',
    'italic',
    'underline',
    'through',
    'color',
    'bgColor',
    'clearStyle',
    '|',
    'fontSize',
    'fontFamily',
    '|',
    'justifyLeft',
    'justifyCenter',
    'justifyRight',
    'justifyJustify',
    '|',
    'lineHeight',
    '|',
    'insertLink',
    'insertImage',
    '|',
    'bulletedList',
    'numberedList',
    '|',
    'blockquote',
    'codeBlock',
    '|',
    'undo',
    'redo'
  ]
}

// 编辑器配置
const editorConfig = {
  placeholder: props.placeholder,
  readOnly: props.disabled,
  maxLength: props.maxCharCount,
  MENU_CONF: {
    
    uploadImage: {
      // 自定义上传
      customUpload: async (file, insertFn) => {
        try {
          const businessId = crypto.randomUUID()
          const fileRes = await uploadFile(file, { businessId, businessType: 'ARTICLE', businessField: 'content' })
          
          if (fileRes.data?.filePath) {
            const imgUrl = filebaseURL + fileRes.data.filePath
            insertFn(imgUrl)
          } else {
            ElMessage.error('图片上传失败')
          }
        } catch (error) {
          ElMessage.error('图片上传失败：' + error.message)
        }
      },
      // 单个文件最大体积
      maxFileSize: 5 * 1024 * 1024,
      // 最多上传几个文件
      maxNumberOfFiles: 10
    },
    insertLink: {
      // 禁止插入外部链接
      checkLink: (url) => {
        return true
      }
    }
  }
}

// 编辑器创建完成
const handleCreated = (editor) => {
  editorRef.value = editor
}

// 编辑器内容变化
const handleChange = (editor) => {
  const html = editor.getHtml()
  content.value = html
  emit('update:modelValue', html)
  emit('change', html)
}

// 编辑器销毁
const handleDestroyed = () => {
  editorRef.value = null
}

// 监听外部内容变化
watch(
  () => props.modelValue,
  (newVal) => {
    if (newVal !== content.value && editorRef.value) {
      editorRef.value.setHtml(newVal)
    }
  }
)

// 组件卸载前销毁编辑器
onBeforeUnmount(() => {
  if (editorRef.value) {
    editorRef.value.destroy()
    editorRef.value = null
  }
})

// 暴露方法
defineExpose({
  // 获取编辑器实例
  getEditor: () => editorRef.value,
  // 获取内容
  getContent: () => content.value,
  // 设置内容
  setContent: (html) => {
    content.value = html
    if (editorRef.value) {
      editorRef.value.setHtml(html)
    }
  },
  // 清空内容
  clear: () => {
    content.value = ''
    if (editorRef.value) {
      editorRef.value.clear()
    }
  },
  // 获取纯文本内容
  getText: () => {
    if (editorRef.value) {
      return editorRef.value.getText()
    }
    return ''
  }
})
</script>

<style lang="scss" scoped>
.rich-text-editor {
  width: 100%;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  overflow: hidden;
  background: #fff;
}

.editor-container {
  display: flex;
  flex-direction: column;
}

.editor-toolbar {
  border-bottom: 1px solid #e4e7ed;
  flex-shrink: 0;
}

.wang-editor {
  flex: 1;
  min-height: 300px;
}

.editor-footer {
  padding: 12px 16px;
  background: #fafafa;
  border-top: 1px solid #e4e7ed;
}

.word-count {
  display: flex;
  align-items: center;
  gap: 12px;
}

.count-text {
  font-size: 13px;
  color: #606266;
}

.process-bar {
  flex: 1;
  height: 6px;
  background: #e4e7ed;
  border-radius: 3px;
  overflow: hidden;

  div {
    height: 100%;
    background: #409eff;
    border-radius: 3px;
    transition: width 0.3s ease;
  }
}
</style>

<style lang="scss">
.wang-editor {
  .w-e-text-container {
    p {
      line-height: 1.75 !important;
      margin: 0 !important;
      padding: 0 !important;
    }

    p:first-child:empty::before {
      content: none;
    }
  }
}
</style>