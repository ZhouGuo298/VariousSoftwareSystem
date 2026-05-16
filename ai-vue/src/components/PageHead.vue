<template>
    <div class="page-head">
        <p class="page-title">{{ props.pageTitle }}</p>
        <div class="action-btn">
            <slot name="buttons"></slot>
        </div>
    </div>
</template>

<script setup>
const props = defineProps({
    pageTitle: {
        type: String,
        default: '标题'
    }
})
</script>

<style lang="scss" scoped>
.page-head {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
    justify-content: space-between;
    .page-title {
        font-size: 24px;
        color: #1f2937;
    }
}
</style>