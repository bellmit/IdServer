const createAutoImport = require('./auto-import')
// const createSvgIcon = require('./svg-icon')
const createCompression = require('./compression')
// const createSetupExtend = require('./setup-extend')


module.exports = (webpackEnv, isBuild = false) => {
    const webpackPlugins = []
    webpackPlugins.push(createAutoImport())
    // webpackPlugins.push(createSetupExtend())
    // webpackPlugins.push(createSvgIcon(isBuild))
    isBuild && webpackPlugins.push(...createCompression(webpackEnv))
    return webpackPlugins
}