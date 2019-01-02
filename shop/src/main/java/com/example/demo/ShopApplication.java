package com.example.demo;

import com.example.demo.model.Post;
import com.example.demo.service.PostManagement;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication implements CommandLineRunner {

    @Autowired PostManagement postManagement;

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (Post post : postManagement.query()) {
            postManagement.delete(post.getId());
        }
        for (int i = 0; i < 3; i++) {
            Post post = new Post();
            post.setId(UUID.randomUUID().toString());
            post.setTitle("Improving cache consistency" + i);
            post.setContent(
                    "A typically web application introduces an in-memory cache like memcache or redis to reduce load on the primary database for reads requesting hot data. The most primitive design looks something like Figure 1.\\nUnfortunately this design is really common despite the many issues it introduces. I’ve seen some organizations with large scale applications still using this design and they maintain a bunch of hacks to overcome these issues which increases the systems operational complexity and sometimes surfaces as inconsistent data to end users.\nIssue 1. Pool of connections to the cache services per web server instance\nIn a large application sometimes thousands of web server instances (especially in slower languages like Ruby) are hosting the web application. Each one has to maintain connections to the infrastructure the web application code communicates with directly. This can include primary databases like MSSQL, MySQL, Oracle, Postgres and cache services like Memcache or Redis. Each web server instance would for example have a pool of connections for each database or cache service instance it communicates with.\nThis can be a strain on resources both on the web server but more importantly the database or cache service as shown in Figure 2. This is why I included a 16,384 connection benchmark in my benchmarks of Redis server libraries for Go to see how they scaled. It’s not uncommon to see 10,000 or 20,000 connections to a Memcache or Redis server in a large system designed like this.\nIssue 2. Many web app requests have to execute cache set operations\nSimilar to how a HTTP request may issue multiple SQL INSERT or UPDATE statements, multiple SET operations may be issued against the cache service. Even though these can be done asynchronously, they still consume resources on the web server and it would be great if the web servers only had to be concerned with updating the primary database.\nIssue 3. No fault tolerance. Data loss if cache set operations fail\nThe typical sequence of operations of how Figure 2 in a web application would be designed would be as follows.\nUpdate the primary database (MSSQL, MySQL, Oracle, Postgres, etc).\nIf the transaction fails return a HTTP error.\nIf the transaction succeeds send SET operations to the cache server(s) (memcache, redis, etc).\nAny SET operation could fail even after retrying which puts the cache service(s) inconsistent with the primary database which could result in users seeing incorrect information. Even worse depending how the application is designed you could experience partial failures which results in users seeing partially correct and partially incorrect information after a change and a cache hit.\nSome cache service protocols support sending multiple SET operations in one command but some do not. Not all web applications are smart enough to group SET operations that happen in different areas of the code into a single command either. If this is the case you could have partial failures where some of the SET operations succeeded and some failed.\nOutside of retrying there’s not much the web application can do to eventually correct the missing cache SET operations. It has to retry and give up at some point. The cache will be serving cache hits that are inconsistent with the primary database until the cache key(s) invalidate via a TTL or some other process.\n");
            postManagement.add(post);
        }
    }
}
