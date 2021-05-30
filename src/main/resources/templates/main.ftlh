<#import "parts/common.ftlh" as c>

<@c.page>
<div class="container">
    <h3 class=" text-center">SimberSoft</h3>
    <div class="messaging">
        <div class="inbox_msg">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Диологи</h4>
                    </div>
                </div>
                <div class="inbox_chat">
                    <div class="chat_list" onclick="loadingRoomById()">
                        <div class="chat_people">
                            <div class="chat_ib">
                                <h5>Бот <span class="chat_date">Вечность</span></h5>
                                <p>Заходи, большой брат всегда поможет тебе...`</p>
                            </div>
                        </div>
                    </div>
                    <#list rooms as room>
                    <div class="chat_list" onclick="loadingRoomById(${room.id})">
                        <div class="chat_people">
                            <div class="chat_ib">
                                <h5>${room.name} <span class="chat_date">Dec 25</span></h5>
                            </div>
                        </div>
                    </div>
                    </#list>
                </div>
            </div>
            <div class="mesgs" id="mesgs">
                <div class="msg_history" id="msg_history">
                    <#if messages?has_content>
                        <#list messages as message>
                        <#if message.userId != user.id>
                            <div class="incoming_msg">
                                <div class="received_msg">
                                    <div class="received_withd_msg">
                                        <h6>${message.userName}</h6>
                                        <p>${message.text}</p>
                                        <span class="time_date"> ${message.date}</span></div>
                                </div>
                            </div>
                        <#else>
                            <div class="outgoing_msg">
                                <div class="sent_msg">
                                    <h6>${user.login}</h6>
                                    <p>${message.text}</p>
                                    <span class="time_date"> ${message.date}</span> </div>
                                </div>
                        </#if>
                        </#list>
                    </#if>
                </div>
                <div class="type_msg">
                    <div class="input_msg_write">
                        <input type="text" class="write_msg" id="write_msg" placeholder="Введите сообщение" />
                        <button class="msg_send_btn" type="button" onclick="sendNewMessage(<#if roomId?has_content && user?has_content>${roomId}, ${user.id}</#if>)"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
              let block = document.getElementById("msg_history");
              block.scrollTop = block.scrollHeight;
            </script>
            <div class="inbox_people_right">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Пользователи</h4>
                    </div>
                </div>
                <div class="inbox_chat">
                    <#list users as user>
                        <div class="chat_list">
                            <div class="chat_people">
                                <div class="chat_ib">
                                    <h5>${user.login}</h5>
                                    <p>${user.role}</p>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>
</@c.page>