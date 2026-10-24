export interface CardData {
    id: number;
    title: string;
    dueDate: Date;
    description: string;
    positionInColumn: number;
    assignees: string[];
}
