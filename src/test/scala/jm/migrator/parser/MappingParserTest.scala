package jm.migrator.parser;

import org.scalatest.FunSpec
import org.scalatest.matchers.MustMatchers

import jm.migrator.domain._

class MappingParserTest extends FunSpec with MustMatchers {

  val parser = new MappingParser
  val url = getClass.getResource("/test_mapping.json").getFile

  describe("MappingParser should parse test mapping "+url+" and return CollectionMapping iterator") {
    val result = parser.parseFile(url)

    it ("should contain one collection mapping named 'users'") {
      result must have size (1)
      result.head.name must be ("users")
      result.head.mapping.fields must have size (10)
      result.head.mapping.fields("_id") must equal (MongoId("u.id", "users"))
      result.head.mapping.fields("stringId") must equal (StringMongoId("u.id", "users"))
      result.head.mapping.fields("oldId") must equal (SimpleValue("u.id"))
      result.head.mapping.fields("username") must equal (SimpleValue("u.username"))
      result.head.mapping.fields("counters.rating") must equal (ToInt("u.rating"))
      result.head.mapping.fields("counters.posts") must equal (Count("posts AS p", "p.user_id = ${oldId}"))
      result.head.mapping.fields("groups") must equal ( Array (
        "groups AS g LEFT JOIN members AS m ON g.id = m.group_id",
        SimpleValue("LOWER(g.slug)"),
        "banned = 0 AND m.user_id = ${oldId}"))
      result.head.mapping.fields("groupPostCount") must equal ( CountMap (
        "posts AS p LEFT JOIN groups AS g ON p.group_id = g.id",
        "g.slug IS NOT NULL AND p.user_id = ${oldId}",
        "LOWER(g.slug)"
      ))
      result.head.mapping.fields("invites") must equal ( Array (
        "group_invites AS i LEFT JOIN groups AS g ON i.group_id = g.id",
        Fields(Map("group" -> SimpleValue("LOWER(g.slug)"), "invitedBy" -> StringMongoId("i.invited_by_id", "users"))),
        "i.user_id = ${oldId}"
      ))
      result.head.mapping.fields("roles") must equal ( ColArray (
        "CASE u.is_admin WHEN 1 THEN 'admin' ELSE NULL END" ::
        "CASE u.is_staff WHEN 1 THEN 'staff' ELSE NULL END" ::
        Nil
      ))
    }

  }

}