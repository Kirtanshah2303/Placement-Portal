import { IUser } from 'app/shared/model/user.model';

export interface ICompanyProfile {
  id?: number;
  email?: string;
  address?: string;
  contactNumber?: number;
  linkedinProfile?: string;
  location?: string;
  technology?: string;
  ctc?: string;
  overview?: string;
  bond?: number;
  minimumCriteria?: string;
  type?: string;
  noOfOpenings?: number;
  user?: IUser | null;
}

export const defaultValue: Readonly<ICompanyProfile> = {};
