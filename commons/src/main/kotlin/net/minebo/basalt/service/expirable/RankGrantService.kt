package net.minebo.basalt.service.expirable

import io.github.nosequel.data.DataStoreType
import net.minebo.basalt.Basalt
import net.minebo.basalt.models.grant.types.RankGrant
import net.minebo.basalt.models.grant.types.scope.GrantScope
import net.minebo.basalt.models.profile.GameProfile
import org.bson.Document
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

object RankGrantService : ExpiringService<RankGrant>()
{

    var handler = Basalt.dataHandler.createStoreType<UUID, RankGrant>(Basalt.getDataStoreMethod())

    val collection =
        Basalt.MongoConnectionPool.getCollection("rankgrant") //need this here because honey doesnt have a way to get raw collection

    var playerGrants = ConcurrentHashMap<UUID, MutableList<RankGrant>>()

    //default grant scope for use in models
    val global: GrantScope = GrantScope("Defaulted Grant Scope (Global)", mutableListOf(), true)

    fun getValues(): CompletableFuture<Collection<RankGrant>>
    {
        return handler.retrieveAllAsync()
    }

    fun findExecutedBy(executor: UUID): MutableList<RankGrant>
    {
        val filter = Document("executor", executor.toString())
        val documents = collection.find(filter)
        val finalGrants = mutableListOf<RankGrant>()

        for (document in documents)
        {
            val obj = Basalt.gson.fromJson(document.toJson(), RankGrant::class.java)

            finalGrants.add(obj)
        }

        return finalGrants
    }

    fun findByRank(id: String): CompletableFuture<MutableList<RankGrant>>
    {
        return CompletableFuture.supplyAsync {
            val filter = Document("rankId", id)
            val documents = collection.find(filter).iterator()
            val finalized = mutableListOf<RankGrant>()

            while (documents.hasNext())
            {
                val next = documents.next()
                val obj = Basalt.gson.fromJson(next.toJson(), RankGrant::class.java)

                finalized.add(obj)
            }

            finalized
        }
    }

    fun getFromCache(uuid: UUID): Collection<RankGrant>
    {
        return if (playerGrants.containsKey(uuid))
        {
            playerGrants[uuid]!!
        } else findByTarget(uuid).get()
    }

    fun recalculatePlayer(gameProfile: GameProfile)
    {
        findByTarget(gameProfile.uuid).thenApply { playerGrants[gameProfile.uuid] = it }
    }

    fun recalculatePlayerSync(gameProfile: GameProfile)
    {
        val grants = findByTarget(gameProfile.uuid).get()

        playerGrants[gameProfile.uuid] = grants
    }

    fun recalculateUUID(gameProfile: UUID)
    {
        findByTarget(gameProfile).whenComplete { grants, e ->
            playerGrants[gameProfile] = grants
        }
    }

    fun remove(grant: RankGrant)
    {
        CompletableFuture.runAsync {
            handler.delete(grant.uuid)
        }.whenComplete { v, t ->
            playerGrants[grant.target]?.remove(grant)
        }
    }

    fun save(rankGrant: RankGrant): CompletableFuture<RankGrant>
    {
        val future = CompletableFuture.supplyAsync {
            handler.store(rankGrant.uuid, rankGrant)

            rankGrant
        }

        return future
    }

    fun saveSync(rankGrant: RankGrant): CompletableFuture<RankGrant>
    {
        handler.store(rankGrant.uuid, rankGrant)

        return CompletableFuture.completedFuture(rankGrant)
    }

    fun findByTarget(target: UUID): CompletableFuture<MutableList<RankGrant>>
    {
        return CompletableFuture.supplyAsync {
            val sorted = collection.find(Document("target", target.toString()))

            val toReturn = mutableListOf<RankGrant>()
            val cursor = sorted.cursor()

            while (cursor.hasNext())
            {
                val document = cursor.next()
                val json = Basalt.gson.fromJson(document.toJson(), RankGrant::class.java)

                toReturn.add(json)
            }

            return@supplyAsync toReturn
        }
    }

    override fun clearOutModels()
    {
    }
}