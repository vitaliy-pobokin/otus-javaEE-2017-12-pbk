import { Message } from "./message";

export class JoinMessage extends Message {
    type: string = "join";
    user: string;
}