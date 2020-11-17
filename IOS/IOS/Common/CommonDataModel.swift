//
//  CommonDataModel.swift
//  IOS
//
//  Created by 김세현 on 2020/09/28.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation

struct ResultModel : Codable{
    var result : String
}

struct CountModel : Codable{
    var count : Int
}

struct LoginModel : Codable{
    var user_id : String
    var user_pw : String
    var user_nickname : String
    var user_gender : String
    var univ_name : String
    var dept_name : String
    var blocking_dept : Int
    var matching_age : String
    var matching_dept : String
    var matching_height : String
    var matching_hobby : String
    var matching_personality : String
    var result : String
}

struct VersionModel : Codable {
    var app_version : String
    var update_issue : String
}
