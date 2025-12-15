module.exports = {
    lintOnSave: false,
    devServer: {
        host: "localhost",
        port: 9090,
        https: false,
        proxy: "http://localhost:9091",
        overlay: {
            warning: false,
            errors: false
        },
    },
    chainWebpack: config => {
    config.module
      .rule('js')
      .test(/\.js$/)
      .include
        .add(/node_modules\/marked/)
        .end()
      .use('babel-loader')
        .loader('babel-loader')
        .options({
          plugins: [
            '@babel/plugin-proposal-optional-chaining',
            '@babel/plugin-proposal-nullish-coalescing-operator'
          ]
        })
  }
}