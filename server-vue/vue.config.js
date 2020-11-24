module.exports = {
    // baseUrl:process.env.NODE_ENV==="prod"?"./":"/",
    assetsDir:"assets",
    configureWebpack:{
        devtool:"source-map"
    },
    devServer:{
        port:8086,
        hot:true,
        proxy:{
            "/":{
                target:"http://127.0.0.1:8080",
                changeOrigin:true,
                pathRewrite:{
                    "^/":"/"
                }
            }
        }
    },
    css:{
      extract:true,
      loaderOptions:{
          sass:{
              javascriptEnable:true
          }
      }

    }
};
