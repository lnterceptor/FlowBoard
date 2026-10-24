import { LabelData } from "./label-data";

export interface BoardSummary {
    id: number;
    title: string;
    description: string;
    collaborators: string[];
    labels: LabelData[];
}
