import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-plus'
import locale from 'element-plus/lib/locale/lang/zh-cn';
import directive from './directive' // directive


import Cookies from 'js-cookie'

// 注册指令
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

// svg 图标 
// import 'virtual:svg-icons-register'
import SvgIcon from '@/components/SvgIcon'
import elementIcons from '@/components/SvgIcon/svgicon'

import { useDict } from '@/utils/dict'
import { parseTime, resetForm, addDateRange, handleTree, selectDictLabel } from '@/utils/ruoyi'

import '@/assets/styles/index.scss' // global css

// 分页组件
import Pagination from '@/components/Pagination'
// 自定义表格工具组件
import RightToolbar from '@/components/RightToolbar'
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
// 图片预览组件
import ImagePreview from "@/components/ImagePreview"
// 自定义树选择组件
import TreeSelect from '@/components/TreeSelect'
// 字典标签组件
import DictTag from '@/components/DictTag'


// import directive from './directive' // directive




const app = createApp(App)


// 全局方法挂载
app.config.globalProperties.useDict = useDict
app.config.globalProperties.download = download
app.config.globalProperties.parseTime = parseTime
app.config.globalProperties.resetForm = resetForm
app.config.globalProperties.handleTree = handleTree
app.config.globalProperties.addDateRange = addDateRange
app.config.globalProperties.selectDictLabel = selectDictLabel




// 全局组件挂载
app.component('DictTag', DictTag)
app.component('Pagination', Pagination)
app.component('TreeSelect', TreeSelect)
app.component('FileUpload', FileUpload)
app.component('ImageUpload', ImageUpload)
app.component('ImagePreview', ImagePreview)
app.component('RightToolbar', RightToolbar)


app.use(plugins)
app.use(elementIcons)
app.component('svg-icon', SvgIcon)

directive(app)


app.use(store)
app.use(router)
app.use(ElementUI, { locale: locale, size: Cookies.get('size') || 'medium' })
app.mount('#app')
