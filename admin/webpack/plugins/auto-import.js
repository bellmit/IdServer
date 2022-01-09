import autoImport from 'unplugin-auto-import/webpack'

export default function createAutoImport() {
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
