/*
 * This file is generated by jOOQ.
 */
package com.zenith.database.dto;


import com.zenith.database.dto.tables.*;
import com.zenith.database.dto.tables.records.PlaytimeAllRecord;
import org.jooq.*;
import org.jooq.impl.SchemaImpl;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.chats</code>.
     */
    public final Chats CHATS = Chats.CHATS;

    /**
     * The table <code>public.connections</code>.
     */
    public final Connections CONNECTIONS = Connections.CONNECTIONS;

    /**
     * The table <code>public.deaths</code>.
     */
    public final Deaths DEATHS = Deaths.DEATHS;

    /**
     * The table <code>public.names</code>.
     */
    public final Names NAMES = Names.NAMES;

    /**
     * The table <code>public.playercount</code>.
     */
    public final Playercount PLAYERCOUNT = Playercount.PLAYERCOUNT;

    /**
     * The table <code>public.playtime_all</code>.
     */
    public final PlaytimeAll PLAYTIME_ALL = PlaytimeAll.PLAYTIME_ALL;

    /**
     * Call <code>public.playtime_all</code>.
     */
    public static Result<PlaytimeAllRecord> PLAYTIME_ALL(
            Configuration configuration
    ) {
        return configuration.dsl().selectFrom(com.zenith.database.dto.tables.PlaytimeAll.PLAYTIME_ALL.call(
        )).fetch();
    }

    /**
     * Get <code>public.playtime_all</code> as a table.
     */
    public static PlaytimeAll PLAYTIME_ALL() {
        return com.zenith.database.dto.tables.PlaytimeAll.PLAYTIME_ALL.call(
        );
    }

    /**
     * The table <code>public.queuelength</code>.
     */
    public final Queuelength QUEUELENGTH = Queuelength.QUEUELENGTH;

    /**
     * The table <code>public.queuewait</code>.
     */
    public final Queuewait QUEUEWAIT = Queuewait.QUEUEWAIT;

    /**
     * The table <code>public.restarts</code>.
     */
    public final Restarts RESTARTS = Restarts.RESTARTS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
                Sequences.RESTARTS_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
                Chats.CHATS,
                Connections.CONNECTIONS,
                Deaths.DEATHS,
                Names.NAMES,
                Playercount.PLAYERCOUNT,
                PlaytimeAll.PLAYTIME_ALL,
                Queuelength.QUEUELENGTH,
                Queuewait.QUEUEWAIT,
                Restarts.RESTARTS);
    }
}
