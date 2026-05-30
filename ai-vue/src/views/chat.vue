<template>
  <div class="chat-page">
    <div class="chat-header">
      <div>
        <h2>AI 咨询</h2>
        <p>由 MaxKB 提供智能问答服务</p>
      </div>
    </div>

    <div class="chat-frame-wrapper">
      <iframe
        class="chat-frame"
        :src="maxkbChatUrl"
        frameborder="0"
        allow="microphone"
      ></iframe>
    </div>
  </div>
</template>

<script setup>
const maxkbChatUrl = 'https://maxkb.lonemonk.xyz/chat/2800d6d7256ba6a7'
</script>

<style scoped>
.chat-page {
  height: calc(100vh - 72px);
  display: flex;
  flex-direction: column;
  background: #f7f2ff;
}

.chat-header {
  padding: 20px 24px;
  background: #fff;
  border-bottom: 1px solid #ebe7f5;
}

.chat-header h2 {
  margin: 0;
  color: #30284f;
  font-size: 22px;
}

.chat-header p {
  margin-top: 6px;
  color: #69627b;
  font-size: 14px;
}

.chat-frame-wrapper {
  flex: 1;
  padding: 16px;
  min-height: 0;
}

.chat-frame {
  width: 100%;
  height: 100%;
  border: 0;
  border-radius: 8px;
  background: #fff;
}
</style>