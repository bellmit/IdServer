const svgIcon = require('vite-plugin-svg-icons')
const path = require('path')

module.exports = (isBuild) => {
    return svgIcon({
        iconDirs: [path.resolve(process.cwd(), 'src/assets/icons/svg')],
        symbolId: 'icon-[dir]-[name]',
        svgoOptions: isBuild
    })
}