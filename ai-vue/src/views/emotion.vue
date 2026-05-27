<template>
    <div>
        <PageHead pageTitle="情绪日志" />
        <Tabelserch :formItem="formItem" @search="handleSearch" />
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="userId" label="用户ID" width="80" />
            <el-table-column label="会话ID" width="80">
                <template #default="scope">
                    <el-avatar>
                        {{ scope.row.nickname }}
                    </el-avatar>
                </template>
            </el-table-column>
            <el-table-column prop="diaryDate" label="记录日期" width="100" />
            <el-table-column label="情绪评分">
                <template #default="scope">
                    <el-rate :model-value="scope.row.moodScore" :max="10" disabled />
                </template>
            </el-table-column>
            <el-table-column label="生活指标" width="120">
                <template #default="scope">
                    <div>
                        <p>睡眠:{{ scope.row.sleepQuality }} / 5</p>
                        <p>压力:{{ scope.row.stressLevel }} / 5</p>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="emotionTriggers" label="情绪触发因素" width="120" />
            <el-table-column prop="diaryContent" label="日记内容" width="200" />
            <el-table-column label="操作" width="200" fixed="right">
                <template #default="scope">
                    <el-button @click="viewSessionDetail(scope.row)" text type="primary">详情</el-button>
                    <el-button @click="handleDelete(scope.row)" text type="danger">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination style="margin-top: 25px;" layout="prev, pager, next"
            v-model:current-page="pagination.currentPage" v-model:page-size="pagination.size" :total="pagination.total"
            @current-change="handleChange" />
        <el-dialog v-model="detailDialogVisible" title="情绪日志详情" width="800px" :close-on-click-modal="false">
            <div v-if="currentDetail">
                <div class="detail-section">
                    <h4>用户信息</h4>
                    <el-descriptions :column="2" :data="currentDetail" border>
                        <el-descriptions-item label="用户名">{{ currentDetail.username }}</el-descriptions-item>
                        <el-descriptions-item label="昵称">{{ currentDetail.nickname }}</el-descriptions-item>
                        <el-descriptions-item label="用户ID">{{ currentDetail.userid }}</el-descriptions-item>
                        <el-descriptions-item label="记录日期">{{ currentDetail.diaryDate }}</el-descriptions-item>
                    </el-descriptions>
                </div>
                <div class="detail-section">
                    <h4>情绪状态</h4>
                    <el-descriptions :column="2" border>
                        <el-descriptions-item label="情绪评分">
                            <el-rate :model-value="currentDetail.moodScore" :max="10" disabled />
                        </el-descriptions-item>
                        <el-descriptions-item label="主要情绪">
                            <el-tag :type="getEmotionTagType(currentDetail.dominantEmotion)">{{
                                currentDetail.dominantEmotion ||
                                '-' }}</el-tag>
                        </el-descriptions-item>
                        <el-descriptions-item label="睡眠质量">{{ currentDetail.sleepQuality || '-'
                            }}/5</el-descriptions-item>
                        <el-descriptions-item label="压力水平">{{ currentDetail.stressLevel || '-'
                            }}/5</el-descriptions-item>
                    </el-descriptions>
                </div>
                <div class="detail-section">
                    <h4>日记内容</h4>
                    <el-descriptions :column="1" border>
                        <el-descriptions-item label="情绪触发因素">{{ currentDetail.emotionTriggers || '无'
                            }}</el-descriptions-item>
                        <el-descriptions-item label="日记内容">{{ currentDetail.diaryContent || '无'
                            }}</el-descriptions-item>
                    </el-descriptions>
                </div>
                <div class="detail-section">
                    <h4>AI情绪分析结果</h4>
                    <div class="ai-analysis-content">
                        <el-descriptions :column="1" border>
                            <el-descriptions-item label="主要情绪">
                                <el-tag :type="getAiEmotionTagType(aiData.primaryEmotion)">{{ aiData.primaryEmotion }}</el-tag>
                            </el-descriptions-item>
                            <el-descriptions-item label="情绪强度">
                                <el-progress :percentage="aiData.emotionScore"
                                    :color="getEmotionScoreColor(aiData.emotionScore)" :stroke-width="8" />
                            </el-descriptions-item>
                            <el-descriptions-item label="风险等级">
                                <el-tag :type="getAiEmotionTagType(aiData.riskLevel)">{{ aiData.riskLevel }}</el-tag>
                            </el-descriptions-item>
                            <el-descriptions-item label="情绪性质">
                                <el-tag :type="aiData.isNegative ? 'danger' : 'success'">{{ aiData.isNegative ? '负面情绪' : '正面情绪'}}</el-tag>
                            </el-descriptions-item>
                        </el-descriptions>
                        <div class="ai-suggestion-section">
                            <h5>专业建议</h5>
                            <div class="suggestion-content">{{ aiData.suggestion || '无' }}</div>
                        </div>
                        <div class="ai-risk-section">
                            <h5>风险描述</h5>
                            <div class="risk-content">{{ aiData.riskDescription || '无' }}</div>
                        </div>
                        <div class="ai-improvements-section">
                            <h5>改善建议</h5>
                            <ul class="improvements-list">
                                <li v-for="item in aiData.improvementSuggestions" :key="item">{{ item }}</li>
                            </ul>
                        </div>
                    </div>

                </div>
                <div class="detail-section">
                    <h4>时间信息</h4>
                    <el-descriptions :column="2" border>
                        <el-descriptions-item label="创建时间">{{ currentDetail.createdAt }}</el-descriptions-item>
                        <el-descriptions-item label="更新时间">{{ currentDetail.updatedAt }}</el-descriptions-item>
                    </el-descriptions>
                </div>
            </div>
            <template #footer>
                <el-button @click="detailDialogVisible = false">关闭</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import PageHead from '@/components/PageHead.vue'
import Tabelserch from '@/components/Tabelserch.vue'
import { categoryTree, getEmotionalPage, deleteEmotional } from '@/api/admin'
import { ElMessageBox } from 'element-plus'

const getEmotionTagType = (emotion) => {
    const emotionTags = {
        '快乐': 'success',
        '平静': 'info',
        '兴奋': 'warning',
        '悲伤': 'info',
        '愤怒': 'danger',
        '焦虑': 'warning',
    }
    return emotionTags[emotion] || 'info'
}

const getAiEmotionTagType = (emotion) => {
    const emotionTagMap = {
        '快乐': 'success',
        '平静': 'success',
        '兴奋': 'warning',
        '满足': 'success',
        '悲伤': 'info',
        '愤怒': 'danger',
        '焦虑': 'warning',
        '恐惧': 'danger',
        '沮丧': 'info',
        '压力': 'warning',
    }
    return emotionTagMap[emotion] || 'info'
}

const getEmotionScoreColor = (score) => {
    if (score >= 80) return '#f56c6c'
    if (score >= 60) return '#e6a23c'
    if (score >= 40) return '#909399'
    return '#67c23a'
}

const getRiskLevelTagType = (risklevel) => {
    const riskTagMap = {
        '0': 'success',
        '1': 'info',
        '2': 'warning',
        '3': 'danger',
    }
    return riskTagMap[risklevel] || 'info'
}

const getRiskLevelText = (risklevel) => {
    const riskLevelMap = {
        '0': '正常',
        '1': '关注',
        '2': '预警',
        '3': '危机',
    }
    return riskLevelMap[risklevel] || '未知风险等级'
}

const formItem = [
    { comp: 'input', prop: 'userId', label: '用户ID', placeholder: '请输入用户ID' },
    {
        comp: 'select', prop: 'moodScore', label: '情绪评分', placeholder: '请输入情绪评分',options: [
        {
            label: '0',
            value: 0    
        },{
            label: '1',
            value: 1
        }, {
            label: '2',
            value: 2
        }, {
            label: '3',
            value: 3
        }, {
            label: '4',
            value: 4
        }, {
            label: '5',
            value: 5    
        }, {
            label: '6',
            value: 6
        }, {
            label: '7',
            value: 7
        }, {
            label: '8',
            value: 8
        }, {
            label: '9',
            value: 9
        }, {
            label: '10',
            value: 10
        }]
    },
]

// 表格数据
const tableData = ref([])
// 分页参数
const pagination = reactive({
    currentPage: 1,
    size: 10,
    total: 0
})

// 分页改变时触发
const handleChange = (page) => {
    pagination.currentPage = page
    handleSearch()
}

const lastSearchParams = ref(null)

const handleSearch = async (formData) => {
    if (formData) {
        lastSearchParams.value = formData
    }

    const currentForm = formData || lastSearchParams.value
    const params = currentForm
        ? { current: pagination.currentPage, size: pagination.size, ...currentForm }
        : { current: pagination.currentPage, size: pagination.size }

    // 过滤掉空字符串参数，避免后端忽略无效参数返回全部数据
    Object.keys(params).forEach(key => {
        if (params[key] === '' || params[key] === null || params[key] === undefined) {
            delete params[key]
        }
    })

    const res = await getEmotionalPage(params)
    const data = res?.data || res
    let records = data?.records || []

    // 前端筛选 moodScore
    if (currentForm && currentForm.moodScore !== '' && currentForm.moodScore !== null && currentForm.moodScore !== undefined) {
        records = records.filter(item => item.moodScore === Number(currentForm.moodScore))
    }

    tableData.value = records
    if (data?.total !== undefined) {
        pagination.total = data.total
    }
}

// 详情弹窗状态
const detailDialogVisible = ref(false)
const currentDetail = ref(null)
const aiData = ref(null)
const viewSessionDetail = (row) => {
    currentDetail.value = row
    if (row.aiEmotionAnalysis) {
        aiData.value = JSON.parse(row.aiEmotionAnalysis)
    } else {
        aiData.value = {}
    }
    detailDialogVisible.value = true
}


// 删除弹窗状态
const handleDelete = (row) => {
    ElMessageBox.confirm('确认删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        // 确认删除
        deleteEmotional(row.id).then(() => {
            handleSearch()
        })
    })
}


onMounted(async () => {
    // 初始化分类映射
    const res = await categoryTree()
    handleSearch()
})

</script>
<style scoped>
.detail-section {
    margin-bottom: 20px;
    padding: 15px;
    background-color: #f8f9fa;
    border-radius: 8px;
}

.detail-section h4 {
    margin: 0 0 15px 0;
    color: #303133;
    font-size: 16px;
    font-weight: 600;
    padding-bottom: 8px;
}

.detail-section h5 {
    margin: 15px 0 10px 0;
    color: #606266;
    font-size: 14px;
}

.ai-analysis-content {
    background-color: #fff;
    padding: 15px;
    border-radius: 6px;
    border: 1px solid #ebeef5;
}

.suggestion-content,
.risk-content {
    padding: 12px;
    background-color: #f0f9ff;
    border-radius: 4px;
    color: #606266;
    line-height: 1.6;
}

.improvements-list {
    margin: 0;
    padding-left: 20px;
}

.improvements-list li {
    padding: 6px 0;
    color: #606266;
    line-height: 1.5;
}
</style>
