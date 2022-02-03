package api

import okhttp3.OkHttpClient


class ClientManager private constructor() {
    companion object {
        val clientManager = ClientManager()
    }
    private val clientsPool: HashMap<Long, OkHttpClient> = HashMap();

    fun getClient(): OkHttpClient {
        if (clientsPool.containsKey(Thread.currentThread().id)) {
            return clientsPool[Thread.currentThread().id]!!;
        }

        val client = OkHttpClient()
        clientsPool[Thread.currentThread().id] = client;
        return client;
    }

}