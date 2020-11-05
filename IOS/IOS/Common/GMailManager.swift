//
//  GmailSender.swift
//  IOS
//
//  Created by 김세현 on 2020/11/05.
//  Copyright © 2020 KSH. All rights reserved.
//

import Foundation
import GoogleAPIClient

class GMailManager {

    let service = GTLServiceGmail()
    
    func sendEmail(to: String, code: String) {

        guard let query = GTLQueryGmail.queryForUsersMessagesSend(with: nil) else {
            return
        }

        let gtlMessage = GTLGmailMessage()
        gtlMessage.raw = self.generateRawString(to: to, code: code)

        query.message = gtlMessage

        self.service.executeQuery(query, completionHandler: { (ticket, response, error) -> Void in
            print("ticket \(String(describing: ticket))")
            print("response \(String(describing: response))")
            print("error \(String(describing: error))")
        })
    }

    func generateRawString(to: String, code: String) -> String {

        let dateFormatter:DateFormatter = DateFormatter()
        dateFormatter.dateFormat = "EEE, dd MMM yyyy HH:mm:ss Z"; //RFC2822-Format
        let todayString:String = dateFormatter.string(from: NSDate() as Date)

        let rawMessage = "" +
            "Date: \(todayString)\r\n" +
            "From: eodrkd12@gmail.com\r\n" +
            "To: \(to)\r\n" +
            "Subject: Uniting 이메일 인증\r\n\r\n" +
            "안녕하세요.\n아래 인증 코드를 애플리케이션에서 입력하여 회원가입을 진행해주십시오.\n인증코드 : [\(code)]\n감사합니다."

        return GTLEncodeWebSafeBase64(rawMessage.data(using: String.Encoding.utf8))
    }
}
