import { CardData } from "./card-data";

export interface ColumnData {
    id: number;
    title: string;
    positionOnBoard: number;
    cards: CardData[];
}
