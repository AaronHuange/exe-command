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
                target:"http://192.168.1.51:8080",
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
