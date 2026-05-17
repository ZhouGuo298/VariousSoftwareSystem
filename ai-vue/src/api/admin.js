import service from '@/utils/request'

export function login(data) {
    return service.post('/user/login', data)
}

export function categoryTree() {
    return service.get('/knowledge/category/tree')
}

// axios对数组的请求参数的格式为params
export function articleList(params) {
    return service.get('/knowledge/article/page', {params})
}
