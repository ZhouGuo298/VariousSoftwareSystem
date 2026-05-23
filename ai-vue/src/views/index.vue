<template>
  <div class="common-layout">
    <el-container>
      <el-header class="index-header">
        <el-button type="primary" class="login-btn">登录</el-button>
        <el-button type="primary" class="knowledge-btn">知识库</el-button>
        <el-button type="primary" class="emotion-btn">情绪日志</el-button>
        <el-button type="primary" class="home-btn">主页</el-button>
        <el-button type="primary" class="github-btn">GitHub</el-button>
      </el-header>
      <el-main class="index-main">Main</el-main>
      <el-footer class="index-footer">Footer</el-footer>
    </el-container>
  </div>
</template>

<script setup>

</script>

<style scoped>
  .common-layout {
    height: 100vh;
    .login-btn {
      margin-left: 10px;
    }
    .knowledge-btn {
      margin-left: 10px;
    }
    .emotion-btn {
      margin-left: 10px;
    }
    .home-btn {
      margin-left: 10px;
    }
    .github-btn {
      margin-left: 10px;
    }
  }
</style>
