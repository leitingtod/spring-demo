```sh
curl -v -u GoShoppingOSClient:secret -X POST http://localhost:8000/oauth/token -H "Accept:application/json" -d "username=admin&password=123&grant_type=password"
```