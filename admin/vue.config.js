const path = require('path')
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
        }
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

}