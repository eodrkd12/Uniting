//
//  UserInfo.swift
//  IOS
//
//  Created by 김세현 on 2020/09/14.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation

class UserInfo : ObservableObject {
    
    static var shared = UserInfo()
    
    var ID: String = "test"
    var PW: String = ""
    var NICKNAME: String = "김세현짱ㅇㅇㅇㅇㅇㅇ"
    var BIRTHDAY: String = ""
    var GENDER: String = "M"
    var UNIV: String = "계명대학교"
    var DEPT: String = "컴퓨터공학전공"
    var BLOCKINGDEPT: Int = 0
}
