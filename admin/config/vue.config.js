'use strict'
console.log('this is vue.config.js header log')
module.export = {
    dev: {
        publicPath: '/', // 部署应用包时的基本 URL， 用法和 webpack 本身的 output.publicPath 一致   
        outputDir: 'dist',
        devServer: {
            host: '0.0.0.0',
            port: 9000,
        },
        //是否在保存的时候使用 `eslint-loader` 进行检查。 
        //有效的值：`ture` | `false` | `"error"`  当设置为 `"error"` 时，检查出的错误会触发编译失败
        lintOnSave: 'true',
    }

}