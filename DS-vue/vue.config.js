module.exports = {
    devServer: {
        open: true, 
        port: 8080,
        hotOnly: false, 
        proxy: {
            '/api': {
                target: "http://localhost:8081",
                changeOrigin: true, 
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
}