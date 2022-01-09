const autoImport = require('unplugin-auto-import/webpack')

module.exports = () => {
    return autoImport({
        imports: [
            'vue',
            'vue-router',
            {
                'vuex': ['useStore']
            }
        ],
        dts: false
    })
}