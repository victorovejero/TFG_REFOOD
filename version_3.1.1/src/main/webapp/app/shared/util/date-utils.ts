import dayjs from 'dayjs';

import { APP_LOCAL_DATETIME_FORMAT } from 'app/config/constants';

export const convertDateTimeFromServer = date => (date ? dayjs(date).format(APP_LOCAL_DATETIME_FORMAT) : null);

export const convertDateTimeToServer = date => (date ? dayjs(date).toDate() : null);

export const displayDefaultDateTime = () => dayjs().startOf('day').format(APP_LOCAL_DATETIME_FORMAT);

export const getToday = (hourBoolean) =>{

  const date = new Date();
  let hour = date.getHours().toString();
  if(Number(hour)<10){
    hour = "0" + hour;
  }
  let min = date.getMinutes().toString();
  if(Number(min)<10) {
    min = "0" + min;
  }
  
  
  let day = date.getDate().toString();
  if(Number(day)<10){
    day = "0" + day;
  }
  let month = (date.getMonth() + 1).toString();
  if(Number(month)<10){
    month = "0" + month;
  }
  const year = date.getFullYear().toString();
  
  const wHour = year + "-" + month + "-" + day + "T" + hour + ":" + min;
  const woHour = year + "-" + month + "-" + day
  
  return hourBoolean ? wHour : woHour;
  
}