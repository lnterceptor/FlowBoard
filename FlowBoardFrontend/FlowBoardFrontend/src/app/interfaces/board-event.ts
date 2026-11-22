import { LabelData } from "./label-data"

export interface CardMovedEvent {
    type: 'CARD_MOVED',
    payload: {
        cardId: number, 
        targetColumnId: number,
        positionInTargetColumn: number
    }
}

export interface CardInColumnMovedEvent {
    type: 'CARD_IN_COLUMN_MOVED',
    cardId: number,
    newPosition: number
}

export interface CardDeleted {
    type: 'CARD_DELETED',
    cardId: number
}

export interface CardCreated{
    type: 'CARD_CREATED',
    payload: {
        title: string,
        description: string,
        positionInColumn: number,
        dueDate: Date,
        labels: LabelData[],
        assignees: string[],
        isDone: boolean,
        cardId: number,
        columnId: number
    }
}
export interface CardEdited{
    type: 'CARD_EDITED',
    payload: {
        title: string,
        description: string,
        positionInColumn: number,
        dueDate: Date,
        labels: LabelData[],
        assignees: string[],
        isDone: boolean,
        cardId: number
    }
}

export interface ColumnMoved {
    type: 'COLUMN_MOVED',
    columnId: number,
    newPosition: number

}
export interface ColumnDeleted{
    type: 'COLUMN_DELETED',
    columnId: number
}
export interface ColumnAdded{
    type: 'COLUMN_ADDED',
    columnId: number,
    columnPosition: number,
    title: string
}

export interface ColumnRenamed{
    type: 'COLUMN_RENAMED',
    columnId: number,
    newName: string
}

export interface AddComment{
    type: 'COMMENT_ADDED',
    content: string,
    userId: number,
    commentId: number,
    username: string
}
export interface UpdateComment{
    type: 'COMMENT_UPDATED',
    commentId: number,
    content: string
}
export interface DeleteComment{
    type: 'COMMENT_DELETED',
    commentId: number
}

export interface BoardDeleted{
    type: 'BOARD_DELETED',
    boardId: number
}

export type BoardEvent = CardMovedEvent | CardInColumnMovedEvent | CardDeleted | CardCreated | CardEdited | ColumnMoved | ColumnAdded | ColumnDeleted | ColumnRenamed
    | AddComment | UpdateComment | DeleteComment | BoardDeleted