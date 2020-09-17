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
    
    var body: some View {
        if chat.user_id == UserInfo.shared.ID {
            return Group {
                
            }
        }
        else {
            return Group {
                
            }
        }
    }
}
