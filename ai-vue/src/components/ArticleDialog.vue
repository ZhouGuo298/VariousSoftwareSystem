<template>
    <div>
      <el-dialog
        title="文章详情"
        v-model="dialogVisible"
        width="50%"
        @close="handleClose"
      >
        <el-form :model="formData" :rules="rules" ref="formRef" label-width="120px">
          <el-form-item label="文章标题" prop="title">
            <el-input v-model="formData.title" placeholder="请输入文章标题" maxlength="200" show-word-limit clearable />
          </el-form-item>
          <el-form-item label="文章分类" prop="categoryId">
            <el-select v-model="formData.categoryId" placeholder="请选择文章分类">
              <el-option v-for="item in props.categories" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="文章摘要" prop="summary">
            <el-input type="textarea" v-model="formData.summary" placeholder="请输入文章摘要(可选)" maxlength="1000" :rows="4" show-word-limit clearable />
          </el-form-item>
          <el-form-item label="标签" prop="tags">
            <el-select v-model="formData.tagArray" placeholder="请选择标签" multiple filterable allow-create width="100%">
              <el-option v-for="item in commonTags" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
          <el-form-item label="封面图片">
            <div class="cover-upload">
                <el-upload
                    class="avatar-uploader"
                    action="#"
                    :before-upload="beforeUpload"
                    :http-request="handleUpload"
                    accept="image/*"
                >
                    <div v-if="!imgUrl" class="cover-placeholder">
                        <p>点击上传封面图片</p>
                    </div>
                    <img v-else :src="imgUrl" alt="封面图片" class="cover-image" />
                </el-upload>
            </div>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
</template>

<script setup>
import { ref, computed,reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { uploadFile } from '@/api/admin'
import { filebaseURL } from '@/config/index'

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  categories: {
    type: Array,
    default: () => []
  }
})



const emit = defineEmits(['update:modelValue'])

const dialogVisible = computed({
  get(){
    return props.modelValue
  },
  set(val){
    emit('update:modelValue', val)
  }
})

// 弹窗关闭时触发
const handleClose = () => {

}

const formData = reactive({
    title: '',
    content: '',
    coverImg: '',
    categoryId: 1,
    summary: '',
    tags: '',
    id: ''
})

const rules = reactive({
    title: [
        { required: true, message: '请输入文章标题', trigger: 'blur' }
        ,{ max: 200, message: '文章标题最多200个字符', trigger: 'blur' }
    ],
    categoryId: [
        { required: true, message: '请选择文章分类', trigger: 'change' }
    ],
})

const commonTags = [
    '情绪管理','焦虑','抑郁','压力','睡眠',
    '冥想','正念','放松','心理健康','自我成长',
    '人际关系','工作压力','学习方法','生活技巧'
]

const imgUrl = ref('')

const beforeUpload = (file) => {
    // 针对图片上传的校验
    const isImage = file.type.startsWith('image/')
    const isLt5MB = file.size / 1024 / 1024 < 5
    if (!isImage) {
      ElMessage.error('请上传图片格式的文件')
      return false
    }
    if (!isLt5MB) {
      ElMessage.error('图片大小不能超过5MB')
      return false
    }
    return true
}
const handleUpload = async ({file}) => {
  const businessId = crypto.randomUUID()
  const fileRes = await uploadFile(file,{businessId:businessId})
  console.log(fileRes)

  const filePath = fileRes.data?.filePath
  imgUrl.value = filebaseURL + filePath
  formData.coverImg = filePath
}



</script>

<style lang="scss" scoped>
.cover-placeholder {
  width: 200px;
  height: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #6b7280;
  background: #f5f7fa;
}
.cover-image {
  width: 200px;
  height: 120px;
  display: block;
}
</style>