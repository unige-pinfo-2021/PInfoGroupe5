import { User } from './user.model';

export interface Group {
	admin: string,
	groupName: string,
	invitation: string,
    users: Array<User>
}

/*
export class GroupObject {
	admin: string,
	groupName: string,
	invitation: string,
    users: Set<User>;

}*/