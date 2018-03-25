import { Component, OnInit } from '@angular/core';
import { Message } from '../message';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  private ws: WebSocket;
  messages: Message[] = [];

  constructor(private messageService: MessageService) { }

  ngOnInit() {
    this.messageService.createObservableSocket("ws://localhost:3000/hw8-websockets/messages")
      .subscribe(body => {
        this.messages.push({ body } as Message)
      });
  }

  sendMessage(message: string): void {
    this.messageService.sendMessage(message);
  }
}
