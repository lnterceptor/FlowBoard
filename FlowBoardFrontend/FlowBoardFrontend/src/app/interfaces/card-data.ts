import { LabelData } from "./label-data";

export interface CardData {
    id: number;
    title: string;
    dueDate: Date;
    description: string;
    positionInColumn: number;
    assignees: string[];
    labels: LabelData[];
    done: boolean;
}
