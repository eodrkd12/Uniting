//
//  ProfileData.swift
//  IOS
//
//  Created by 김세현 on 2020/09/14.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ProfileData : Codable {
    var user_id : String
    var user_nickname : String
    var user_birthday : String
    var dept_name : String
    var user_gender : String
    var enter_year : String
    var user_city : String
    var user_hobby : String?
    var user_personality : String?
    var user_introduce : String?
    var user_height : String?
    var user_signdate : String
    var univ_name : String
    var user_email : String
}
