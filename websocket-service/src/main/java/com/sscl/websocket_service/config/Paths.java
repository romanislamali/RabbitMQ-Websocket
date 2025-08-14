package com.sscl.websocket_service.config;

public class Paths {
    public static final String NOTIFICATION = "notification";

    public static final String NOTIFICATION_QUEUE = "notification.queue ";
    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";
    public static final String ROUTING_KEY = "notify.#";
    public static final String END_POINT = "/ws";
    public static final String TOPIC = "/topic";
    public static final String QUEUE = "/queue";
    public static final String APP = "/app";
    public static final String TOPIC_ROLE = "/topic/role/";
    public static final String VIEWER_ROLE = "/{viewerRole}";
    public static final String GROUP_ID = "/{groupId}";
    public static final String CUSTOMER_CLINT_PORT = "http://localhost:4201";
    public static final String BANK_CLINT_PORT = "http://localhost:4200";
    public static final String SEND_NOTIFICATION = "/send-notification";
    public static final String FETCH_ALL_NOTIFICATIONS = "/fetch-all-notifications";
    public static final String QUEUE_ALL_NOTIFICATIONS = "/queue/all-notifications";
    public static final String MARK_AS_READ = "/mark-as-read";
    public static final String DELETE = "/delete";
}
