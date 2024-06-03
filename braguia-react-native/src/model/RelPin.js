import { field, text } from '@nozbe/watermelondb/decorators'
import { Model } from '@nozbe/watermelondb'

export default class Rel_pin extends Model {
    static table = 'rel_pin'
    static associations = {
        rel_pins: { type: 'belongs_to', foreignKey: 'pin_id' },
    }

    @text('rel_pin_id') rel_pin_id
    @text('value') value
    @text('attrib') attrib
    // FIX: add relations
}