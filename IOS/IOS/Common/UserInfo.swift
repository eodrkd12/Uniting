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
    
    var ID: String = ""
    var PW: String = ""
    var NICKNAME: String = ""
    var BIRTHDAY: String = ""
    var GENDER: String = ""
    var UNIV: String = ""
    var DEPT: String = ""
    var BLOCKINGDEPT = 0
}
