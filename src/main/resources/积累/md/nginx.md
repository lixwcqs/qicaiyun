##文档

[Nginx中文文档](http://www.nginx.cn/doc/)

##常用命令
- 启动：
    
    > sudo /usr/local/nginx/sbin/nginx
    
- 停止
    + 完整停止
        > sudo /usr/local/nginx/sbin/nginx -s quit
    
    + 快速停止
 
        > sudo /usr/local/nginx/sbin/nginx -s stop

- 重启
    + 先停止后启动（推荐）
        > sudo /usr/local/nginx/sbin/nginx -s quit
        
        >  sudo /usr/local/nginx/sbin/nginx
    + 重新加载配置文件
        > sudo /usr/local/nginx/sbin/nginx -s reload

- 查看进程
    >  ps aux|grep nginx
    
   