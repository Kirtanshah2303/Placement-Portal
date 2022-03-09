import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IStudentProfile {
  id?: number;
  studentID?: string;
  personalEmail?: string;
  address?: string;
  contactNumber?: number;
  linkedinProfile?: string;
  dob?: string;
  location?: string;
  githubProfile?: string;
  cgpa?: number;
  noOfBacklogs?: number;
  user?: IUser | null;
}

export const defaultValue: Readonly<IStudentProfile> = {};
