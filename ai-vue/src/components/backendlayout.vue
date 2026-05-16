<template>
  <div class="common-layout">
    <el-container class="main-container">
      <el-menu>
        <Sidebar />
      </el-menu>
      <el-container>
        <el-header>
          <Navbar />
        </el-header>
        <el-main class="main-content">
         <router-view class="content-container"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import Sidebar from './Sidebar.vue'
import Navbar from './Navbar.vue'



</script>

<style lang="scss" scoped>
.common-layout {
  height: 100vh;
  .el-header {
    height: 74px !important;
  }
  .main-container {
    height: 100%;
    .content-container {
      padding: 20px;
      background-color: #fff;
      min-height: calc(100% - 74px);
    }
  }
}
</style>