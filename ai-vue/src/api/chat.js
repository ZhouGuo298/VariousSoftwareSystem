import service from '@/utils/request'

export function getAgentProfile() {
    return service.get('/chat/api/application/profile')
}

export function getSessionId(agentId) {
    return service.get('/chat/api/open', { params: { agent_id: agentId } })
}

export function chatMessage(chatId, data) {
    return service.post(`/chat/api/chat_message/${chatId}`, data)
}