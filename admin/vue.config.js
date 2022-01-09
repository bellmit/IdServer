const path = require('path')
const createAutoImport = require('./webpack/plugins')

module.exports = {
    publicPath: './',
    devServer: {
        host: 'localhost'
    },
    configureWebpack: {
        devtool: "inline-source-map",
        resolve: {
            alias: {
                // 设置路径
                // '~': path.resolve(__dirname, './'),
                // 设置别名
                '@': path.resolve(__dirname, "./src")
            }
        },
        plugins: createAutoImport()
    },
    chainWebpack: config => {
        // 一个规则里的 基础Loader
        // svg是个基础loader
        const svgRule = config.module.rule('svg')

        // 清除已有的所有 loader。
        // 如果你不这样做，接下来的 loader 会附加在该规则现有的 loader 之后。
        svgRule.uses.clear()

        // 添加要替换的 loader
        svgRule
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'icon-[name]'
            })
    },
    css: {
        extract: false,
        loaderOptions: {
            // sass: {
            //     prependData: '@import "~@/assets/styles/variables.scss";'
            // },
            // scss: {
            //     prependData: '@import "~@/assets/styles/variables.scss";'
            // }
        }
    },
    lintOnSave: false
}