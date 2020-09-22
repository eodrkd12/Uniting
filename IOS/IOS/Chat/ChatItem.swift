//
//  ChatItem.swift
//  IOS
//
//  Created by 김세현 on 2020/09/17.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ChatItem: View {
    
    static var befItem : ChatItem? = nil
    
    @State var chat : ChatData
    @State var imageVisible : Bool = true
    @State var nicknameVisible : Bool = true
    @State var timeVisible : Bool = true
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
                        Text(chatTime)
                            .font(.system(size: 12))
                            .foregroundColor(Colors.grey500)
                    }
                    Text(chat.chat_content)
                        .font(.system(size:18))
                        .foregroundColor(Color.white)
                        .padding(.horizontal, 5)
                        .padding(.vertical, 5)
                        .background(Colors.primary)
                        .cornerRadius(10)
                }
            }
            else {
                HStack(spacing: 10){
                    if imageVisible {
                        Image("mood_bad-24px")
                            .resizable()
                            .frame(width: 20, height: 20)
                            .clipShape(Circle())
                    }
                    VStack(spacing: 10){
                        if nicknameVisible {
                            HStack{
                                Text(chat.user_nickname)
                                    .font(.system(size: 12))
                                    .foregroundColor(Colors.grey500)
                                Spacer()
                            }
                            .frame(width: 70)
                        }
                        Text(chat.chat_content)
                            .font(.system(size:18))
                            .foregroundColor(Colors.grey700)
                            .padding(.horizontal, 5)
                            .padding(.vertical, 5)
                            .background(Colors.grey300)
                            .cornerRadius(10)
                    }
                    VStack(alignment: .leading){
                        Spacer()
                        Text(unreadCount > 0 ? "\(unreadCount)" : "")
                            .font(.system(size: 12))
                            .foregroundColor(Color.yellow)
                        Text(chatTime)
                            .font(.system(size: 12))
                            .foregroundColor(Colors.grey500)
                    }
                    Spacer()
                }
            }
        }
        .onAppear(){
            if ChatItem.befItem == nil {
                self.imageVisible = true
                self.nicknameVisible = true
                self.timeVisible = true
            }
            else if ChatItem.befItem?.chat.user_id == self.chat.user_id {
                self.imageVisible = false
                self.nicknameVisible = false
                ChatItem.befItem?.timeVisible = false
                self.timeVisible = true
            }
            else {
                self.imageVisible = true
                self.nicknameVisible = true
                self.timeVisible = true
            }
            var time = self.chat.chat_time.split(separator: " ")[1]
            var hour = Int(time.split(separator: ":")[0])
            var timeStr = ""
            
            
            if hour! >= 12 {
                timeStr = "오후 \(hour!-12):\(Int(time.split(separator: ":")[1]).unsafelyUnwrapped)"
            }
            else {
                timeStr = "오전 \(hour!):\(Int(time.split(separator: ":")[1]).unsafelyUnwrapped)"
            }
            
            self.chatTime = timeStr
            
            self.unreadCount = self.chat.unread_member.split(separator: "|").count
        }
    }
}
