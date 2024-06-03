import { field, text } from '@nozbe/watermelondb/decorators'
import { Model } from '@nozbe/watermelondb'

export default class Contact extends Model {
    static table = 'contacts'
  
    @text('name') name
    @text('phone') phone
    @text('url') url
    @text('mail') mail
    @text('desc') desc
    @text('app') app
  }
  
  
  