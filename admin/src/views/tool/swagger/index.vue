<template>
   <i-frame v-model:src="url"></i-frame>
</template>

<script setup>
import iFrame from '@/components/iFrame'

const url = ref(process.env.VITE_APP_BASE_API + "/swagger-ui/index.html")
</script>
