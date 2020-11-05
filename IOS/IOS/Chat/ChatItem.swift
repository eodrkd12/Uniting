//
//  ChatItem.swift
//  IOS
//
//  Created by 김세현 on 2020/09/17.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ChatItem: View {
    @State var chat : ChatData
    @State var nicknameVisible : Bool
    @State var timeVisible : Bool
    @State var chatTime = ""
    @State var unreadCount = 0
    
    var body: some View {
        return Group {
            if chat.user_id == UserInfo.shared.ID {
                HStack(spacing: 10){
                    Spacer()
                    VStack(alignment: .trailing){
                        Spacer()
                        Text(unreadCount > 0 ? "\(unreadCount)" : "")
                            .font(.system(size: 12))
                            .foregroundColor(Color.yellow)
                        Text(timeVisible == true ? chatTime : "")
                            .font(.system(size: 12))
                            .foregroundColor(Colors.grey500)
                    }
                    Text(chat.chat_content)
                        .font(.system(size:20))
                        .foregroundColor(Color.white)
                        .padding(.horizontal, 5)
                        .padding(.vertical, 5)
                        .background(Colors.primary)
                        .cornerRadius(10)
                }
            }
            else {
                if chat.system_chat == 0 {
                    HStack(spacing: 10){
                        VStack(alignment:.leading, spacing: 10){
                            if nicknameVisible {
                                Text(chat.user_nickname)
                                    .font(.system(size: 14))
                                    .foregroundColor(Colors.grey500)
                            }
                            HStack{
                                Text(chat.chat_content)
                                    .font(.system(size:20))
                                    .foregroundColor(Colors.grey700)
                                    .padding(.horizontal, 5)
                                    .padding(.vertical, 5)
                                    .background(Colors.grey300)
                                    .cornerRadius(10)
                            }
                            Spacer()
                        }
                        VStack(alignment: .leading){
                            Spacer()
                            Text(unreadCount > 0 ? "\(unreadCount)" : "")
                                .font(.system(size: 12))
                                .foregroundColor(Color.yellow)
                            Text(timeVisible == true ? chatTime : "")
                                .font(.system(size: 12))
                                .foregroundColor(Colors.grey500)
                        }
                        Spacer()
                    }
                }
                else {
                    Text(chat.chat_content)
                        .font(.system(size: 14))
                        .foregroundColor(Colors.grey500)
                        .padding(5)
                }
            }
        }
        .onAppear(){
            var time = self.chat.chat_time.split(separator: " ")[1]
            var hour = Int(time.split(separator: ":")[0])
            var timeStr = ""
            
            
            if hour! >= 12 {
                timeStr = "오후 \(hour!-12):\(time.split(separator: ":")[1])"
            }
            else {
                timeStr = "오전 \(hour!):\(time.split(separator: ":")[1])"
            }
            
            self.chatTime = timeStr
            
            self.unreadCount = self.chat.unread_member.split(separator: "|").count
        }
    }
    
    
}
