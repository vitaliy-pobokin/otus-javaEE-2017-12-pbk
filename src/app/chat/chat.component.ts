import { Component, OnInit } from '@angular/core';
import { Message } from '../message';
import { JoinMessage } from '../joinMessage';
import { ChatMessage } from '../chatMessage';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  private ws: WebSocket;
  user: string = "";
  users: string[];
  messages: Message[] = [];

  constructor(private messageService: MessageService) { }

  ngOnInit() {
    this.messageService.createObservableSocket("ws://localhost:3000/hw8-websockets/messages")
      .subscribe(str => {
        console.log('Message received: ' + str);
        let message = JSON.parse(str);
        if (message.type === "chat" || message.type === "info") {
          this.messages.push(message);
        } else if (message.type === "users") {
          this.users = message.users;
        }
      });
  }

  joinChat(username: string): void {
    this.user = username;
    let msg = new JoinMessage();
    msg.user = username;
    this.sendMessage(JSON.stringify(msg));
  }

  sendChatMessage(text: string): void {
    let msg = new ChatMessage();
    msg.from = this.user;
    msg.to = "";
    msg.text = text;
    this.sendMessage(JSON.stringify(msg));
  }

  private sendMessage(message: string): void {
    console.log('About to send message: ' + message);
    this.messageService.sendMessage(message);
  }
}
