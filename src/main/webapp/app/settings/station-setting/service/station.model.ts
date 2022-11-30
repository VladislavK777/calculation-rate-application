import {Department} from "../../common-sevice/model/department.model";
import {Road} from "../../common-sevice/model/road.model";

export interface Station {
    id: number;
    code: string;
    name: string;
    department: Department | null;
    road: Road;
}
