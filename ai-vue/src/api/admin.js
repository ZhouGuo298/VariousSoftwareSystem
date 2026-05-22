import service from '@/utils/request'

export function login(data) {
    return service.post('/user/login', data)
}

export function register(data) {
    return service.post('/user/register', data)
}

export function categoryTree() {
    return service.get('/knowledge/category/tree')
}

// axios对数组的请求参数的格式为params
export function articleList(params) {
    return service.get('/knowledge/article/page', {params})
}

export function uploadFile(file,businessInfo) {
    const formData = new FormData()
    formData.append('file', file)
    formData.append('businessType', 'ARTICLE')
    formData.append('businessId', businessInfo.businessId)
    formData.append('businessField','cover')

    return service.post('/file/upload', formData,{headers:{'Content-Type':'multipart/form-data'}})
}

export function addArticle(data) {
    return service.post('/knowledge/article', data)
}

export function getArticleDetail(id) {
    return service.get(`/knowledge/article/${id}`)
}