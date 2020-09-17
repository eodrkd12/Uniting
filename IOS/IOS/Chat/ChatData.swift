//
//  ChatData.swift
//  IOS
//
//  Created by 김세현 on 2020/09/17.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ChatData: Codable {
    var chat_id : String
    var room_id : String
    var user_id : String
    var user_nickname : String
    var chat_content : String
    var chat_time : String
    var unread_member : String
    var system_chat : Int
}
