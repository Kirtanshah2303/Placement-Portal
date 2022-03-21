import { IStudentProfile } from 'app/shared/model/student-profile.model';
import { ICompanyProfile } from 'app/shared/model/company-profile.model';

export interface IStudentCompanyStatus {
  id?: number;
  ctc?: string;
  location?: string;
  status?: string;
  student?: IStudentProfile | null;
  company?: ICompanyProfile | null;
}

export const defaultValue: Readonly<IStudentCompanyStatus> = {};
